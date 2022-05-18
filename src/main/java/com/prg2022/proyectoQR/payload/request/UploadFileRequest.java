package com.prg2022.proyectoQR.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileRequest {
    private MultipartFile[] fileDatas;
    public MultipartFile[] getFileDatas() { return fileDatas; }
    public void setFileDatas(MultipartFile[] fileDatas) { this.fileDatas = fileDatas; } 
}
