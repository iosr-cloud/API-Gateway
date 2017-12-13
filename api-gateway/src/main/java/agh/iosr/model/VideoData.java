package agh.iosr.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "video_data")
@Data
@NoArgsConstructor
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
    @Enumerated(value = EnumType.STRING)
    private VideoConversionType videoConversionType;

    @Column(name = "status")
    private boolean status;

    @Column(name = "converted_file_path")
    private String convertedFilePath;

    public VideoData(String userId, String filmName, String filePath, VideoConversionType videoConversionType) {
        this.userId = userId;
        this.filmName = filmName;
        this.filePath = filePath;
        this.videoConversionType = videoConversionType;
    }

    public VideoData(String userId, String filmName, String filePath, VideoConversionType videoConversionType, boolean status) {
        this.userId = userId;
        this.filmName = filmName;
        this.filePath = filePath;
        this.videoConversionType = videoConversionType;
        this.status = status;
    }
}
