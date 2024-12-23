package com.woozuda.backend.alarm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class AlarmService {

    // 메모리 저장소 (서버 켤 때 마다 일관된 값을 들고 있는게 아니라, 연결 자체를 기록하는 것이므로 메모리 저장소도 괜찮음)
    // 서버가 여러 대로 늘어나면 redis 도입을 해 봐야함
    //
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter connect(String username){

        SseEmitter emitter = new SseEmitter();

        emitters.put(username, emitter);

        //emitter 가 완료 되거나 타임아웃 되면 리스트에서 제거
        emitter.onCompletion(() -> emitters.remove(username));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(username);
        });

        // 초기 1회는 답을 해주어야 함
        try{
            emitter.send(SseEmitter.event()
                    .name("CONNECTED")
                    .data("SSE connect"));
        }catch(IOException e){
            log.info("초기 연결 메세지 못보냈음");
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void alarmTest(String username){
        SseEmitter emitter = emitters.get(username);

        if(emitter == null){
            return;
        }

        try{
            emitter.send(SseEmitter.event()
                    .name("alarm_msg")
                    .data("테스트 알람 메세지 입니다."));
        }catch(IOException e){
            log.info("테스트 알람 메세지 못보냈음");
            emitters.remove(username);
        }
    }
}
