package SADYz.backend.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DnkRequestDto {
    private String apikey;
    private String channel;
    private List<DnkRequestBody> req_data;

}
