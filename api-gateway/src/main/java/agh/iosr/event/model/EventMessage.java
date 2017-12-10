package agh.iosr.event.model;

import agh.iosr.model.VideoConversionType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class EventMessage implements Serializable{

    private final String userId;
    private final String resourceURL;
    private final VideoConversionType conversionType;
}
