package agh.iosr.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "video_data")
@Data
public class VideoData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "film_name")
    private String filmName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "conversion_type")
    private VideoConversionType videoConversionType;

    @Column(name = "status")
    private String status;

    @Column(name = "converted_file_path")
    private String convertedFilePath;

    public VideoData(String userId, String filmName, String filePath, VideoConversionType videoConversionType) {
        this.userId = userId;
        this.filmName = filmName;
        this.filePath = filePath;
        this.videoConversionType = videoConversionType;
    }
}
