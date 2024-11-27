package com.echoteam.app.entities.dto.nativeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AvatarDTO {
    private Long id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String contentType;
    private Timestamp createdAt;
    private byte[] content;
}
