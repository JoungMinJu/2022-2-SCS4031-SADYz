import pathlib

import gluonnlp as nlp
import numpy as np
import torch
from torch.utils.data import Dataset
from transformers import AutoTokenizer, AutoModel

root_path = str(pathlib.Path(__file__).parent.parent.absolute())

# Setting parameters
max_len = 64
batch_size = 64
warmup_ratio = 0.1
num_epochs = 5
max_grad_norm = 1
log_interval = 200
learning_rate = 5e-5

tokenizer = AutoTokenizer.from_pretrained("skt/kobert-base-v1")
bertmodel = AutoModel.from_pretrained("skt/kobert-base-v1")
vocab = nlp.vocab.BERTVocab.from_sentencepiece(tokenizer.vocab_file, padding_token='[PAD]')
tok = tokenizer.tokenize


class BERTClassifier(torch.nn.Module):
    def __init__(self,
                 bert,
                 hidden_size=768,
                 num_classes=7,  ##클래스 수 조정##
                 dr_rate=None,
                 params=None):
        super(BERTClassifier, self).__init__()
        self.bert = bert
        self.dr_rate = dr_rate

        self.classifier = torch.nn.Linear(hidden_size, num_classes)
        if dr_rate:
            self.dropout = torch.nn.Dropout(p=dr_rate)

    def gen_attention_mask(self, token_ids, valid_length):
        attention_mask = torch.zeros_like(token_ids)
        for i, v in enumerate(valid_length):
            attention_mask[i][:v] = 1
        return attention_mask.float()

    def forward(self, token_ids, valid_length, segment_ids):
        attention_mask = self.gen_attention_mask(token_ids, valid_length)

        _, pooler = self.bert(input_ids=token_ids, token_type_ids=segment_ids.long(),
                              attention_mask=attention_mask.float().to(token_ids.device), return_dict=False)
        if self.dr_rate:
            out = self.dropout(pooler)
        return self.classifier(out)


ctx = "cuda" if torch.cuda.is_available() else "cpu"
device = torch.device(ctx)

# BERT 모델 불러오기
model = BERTClassifier(bertmodel, dr_rate=0.5).to(device)

emotion_clsf_weights_file = f"{root_path}\checkpoint\emotion_p3.pth"
model.load_state_dict(torch.load(emotion_clsf_weights_file, map_location=device))


class BERTDataset(Dataset):
    def __init__(self, dataset, sent_idx, label_idx, bert_tokenizer, vocab, max_len,
                 pad, pair):
        # print(vocab)
        transform = nlp.data.BERTSentenceTransform(
            bert_tokenizer, max_seq_length=max_len, vocab=vocab, pad=pad, pair=pair)

        self.sentences = [transform([i[sent_idx]]) for i in dataset]
        self.labels = [np.int32(i[label_idx]) for i in dataset]

    def __getitem__(self, i):
        return (self.sentences[i] + (self.labels[i],))

    def __len__(self):
        return (len(self.labels))


def predict(predict_sentence):
    data = [predict_sentence, '0']
    dataset_another = [data]

    another_test = BERTDataset(dataset_another, 0, 1, tok, vocab, max_len, True, False)
    test_dataloader = torch.utils.data.DataLoader(another_test, batch_size=batch_size, num_workers=0)

    model.eval()

    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(test_dataloader):
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)

        valid_length = valid_length
        label = label.long().to(device)

        out = model(token_ids, valid_length, segment_ids)

        test_eval = []
        args_max = []
        for i in out:
            logits = i
            logits = logits.detach().cpu().numpy()

            args_max.append(np.argmax(logits))
            if np.argmax(logits) == 0:
                test_eval.append("공포가")
            elif np.argmax(logits) == 1:
                test_eval.append("놀람이")
            elif np.argmax(logits) == 2:
                test_eval.append("분노가")
            elif np.argmax(logits) == 3:
                test_eval.append("슬픔이")
            elif np.argmax(logits) == 4:
                test_eval.append("중립이")
            elif np.argmax(logits) == 5:
                test_eval.append("행복이")
            elif np.argmax(logits) == 6:
                test_eval.append("혐오가")
        return args_max[0]
        # print(">> 입력하신 내용에서 " + test_eval[0] + " 느껴집니다.")


# # 질문 무한반복하기! 0 입력시 종료
# end = 1
# while end == 1:
#     sentence = input("하고싶은 말을 입력해주세요 : ")
#     if sentence == "0":
#         break
#     predict(sentence)
#     print("\n")
