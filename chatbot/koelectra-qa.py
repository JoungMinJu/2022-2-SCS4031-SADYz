import pathlib

import torch
import torch.nn as nn
import random

import torch
from transformers import (
  ElectraConfig,
  ElectraTokenizer
)
from model.koelectra import koElectraForSequenceClassification,koelectra_input

root_path = str(pathlib.Path(__file__).parent.absolute())


def load_wellness_answer():
    category_path = f"{root_path}/data/wellness_dialog_category.txt"
    answer_path = f"{root_path}/data/wellness_dialog_answer.txt"
    c_f = open(category_path, 'r', encoding='UTF8')
    a_f = open(answer_path, 'r',  encoding='UTF8')

    category_lines = c_f.readlines()
    answer_lines = a_f.readlines()

    category = {}
    answer = {}
    for line_num, line_data in enumerate(category_lines):
        data = line_data.split('    ')
        category[data[1][:-1]] = data[0]

    for line_num, line_data in enumerate(answer_lines):
        data = line_data.split('    ')
        keys = answer.keys()
        if (data[0] in keys):
            answer[data[0]] += [data[1][:-1]]
        else:
            answer[data[0]] = [data[1][:-1]]

    return category, answer

categort, answer = load_wellness_answer()


checkpoint_path =f"{root_path}/checkpoint"
save_ckpt_path = f"{checkpoint_path}/koelectra-wellness-text-classification-20201023.pth"
model_name_or_path = "monologg/koelectra-base-discriminator"

#답변과 카테고리 불러오기
category = []
idx = -1
with open(root_path+'/data/wellness_data_for_text_classification.txt', 'r', encoding='UTF8') as f:
  while True:
    line = f.readline()
    if not line:
      break
    datas = line.strip().split("\t")
    if datas[1] != str(idx):
      category.append(datas[2])
      idx += 1

ctx = "cuda" if torch.cuda.is_available() else "cpu"
device = torch.device(ctx)

# 저장한 Checkpoint 불러오기
checkpoint = torch.load(save_ckpt_path, map_location=device)


# Electra Tokenizer
tokenizer = ElectraTokenizer.from_pretrained(model_name_or_path)

electra_config = ElectraConfig.from_pretrained(model_name_or_path)
model = koElectraForSequenceClassification.from_pretrained(pretrained_model_name_or_path=model_name_or_path,
                                                            config=electra_config,
                                                            num_labels=359)


model.load_state_dict(checkpoint['model_state_dict'], strict=False)
model.to(device)
model.eval()

while 1:
  sent = input('\nQuestion: ') # '요즘 기분이 우울한 느낌이에요'
  data = koelectra_input(tokenizer,sent, device,512)
  # print(data)

  output = model(**data)

  logit = output
  softmax_logit = nn.Softmax(logit).dim
  softmax_logit = softmax_logit[0].squeeze()

  max_index = torch.argmax(softmax_logit).item()
  max_index_value = softmax_logit[torch.argmax(softmax_logit)].item()


  tmp_category = category[max_index]
  answer_list = answer[tmp_category]
  answer_len= len(answer_list)-1
  answer_index = random.randint(0,answer_len)
  print(f'Answer: {answer_list[answer_index]}, index: {max_index}, value: {max_index_value}')
  print(f'index: {category[max_index]}, value: {max_index_value}')
  print('-'*50)
# print('argmin:',softmax_logit[torch.argmin(softmax_logit)])
