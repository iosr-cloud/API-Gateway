package agh.iosr.repository;

import agh.iosr.model.VideoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoDataRepository extends JpaRepository<VideoData, Long> {

    @Query("select v.status from VideoData v where v.id = :Id")
    boolean findStatusById(@Param("Id") long Id);
}
