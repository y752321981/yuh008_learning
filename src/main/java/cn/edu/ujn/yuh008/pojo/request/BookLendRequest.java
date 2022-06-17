package cn.edu.ujn.yuh008.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.ServerSocket;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookLendRequest {
    private String bookLendId;
    private String username;
    private String bookItemId;
    private String lastReturnTime;
}
