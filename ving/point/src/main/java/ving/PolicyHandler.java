package ving;

import ving.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    //Bean 간 연결
    @Autowired
    PointRepository mileageRepository;
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelled_remove(@Payload Cancelled cancelled){

        if(cancelled.isMe()){
            System.out.println("##### listener  : " + cancelled.toJson());

            Optional<Point> optPoint = mileageRepository.findById(cancelled.getId());
            Point point = optPoint.get();

            mileageRepository.delete(point);
        }
    }
}
