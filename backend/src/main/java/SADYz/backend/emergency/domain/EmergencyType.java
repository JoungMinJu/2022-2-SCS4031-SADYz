package SADYz.backend.emergency.domain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EmergencyType {
    button("응급콜 버튼 누름"),
    no_response("안부 질문에 응답 없음"),
    no_move("움직임이 장기간 감지되지 않음");

    private final String content;

    private static final Map<String, EmergencyType> BY_CONTENT =
            Stream.of(values()).collect(Collectors.toMap(EmergencyType::getContent, Function.identity()));

    EmergencyType(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public static EmergencyType valueOfContent(String content){
        return BY_CONTENT.get(content);
    }

}
