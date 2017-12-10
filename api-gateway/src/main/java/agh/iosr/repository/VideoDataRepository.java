package agh.iosr.repository;

import agh.iosr.model.VideoData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDataRepository extends JpaRepository<VideoData, Long> {
}
