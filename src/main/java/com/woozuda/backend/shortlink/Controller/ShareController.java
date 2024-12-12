package com.woozuda.backend.shortlink.Controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.image.dto.ImageDto;
import com.woozuda.backend.note.entity.*;
import com.woozuda.backend.note.entity.type.*;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.note.repository.QuestionRepository;
import com.woozuda.backend.shortlink.Service.ShareService;
import com.woozuda.backend.shortlink.dto.NoteIdDto;
import com.woozuda.backend.shortlink.dto.SharedNoteByDateDto;
import com.woozuda.backend.shortlink.dto.SharedNoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.woozuda.backend.account.entity.AiType.PICTURE_NOVEL;

@RequestMapping("/api/shared")
@RestController
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/note")
    public ResponseEntity<Void> makeSharedNote(@RequestBody NoteIdDto noteIdDto) {
        shareService.makeSharedNote(noteIdDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/note")
    public ResponseEntity<List<SharedNoteByDateDto>> getSharedNote(@AuthenticationPrincipal CustomUser customUser){
        String username = customUser.getUsername();
        List<SharedNoteByDateDto> dtos = shareService.getSharedNote(username);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }



    //// 아래 부턴 실험을 위한 샘플 코드 입니다 .


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/init")
    public void initSharedNote(){
        //given - 데이터 넣기 (user 1명, diary 1개, question 1개 ,note 5개)
        UserEntity user1 = new UserEntity(null, "woozuda@gmail.com", "1234", "ROLE_ADMIN", PICTURE_NOVEL);
        UserEntity user2 = new UserEntity(null, "rodom1018@gmail.com", "1234", "ROLE_ADMIN", PICTURE_NOVEL);
        userRepository.save(user1);
        userRepository.save(user2);

        Diary diary1 = Diary.of(user1, "https://woozuda-image.kr.object.ncloudstorage.com/random-image-1.jpg", "my first diary");
        Diary diary2 = Diary.of(user2, "https://woozuda-image.kr.object.ncloudstorage.com/random-image-1.jpg", "my first diary22");
        diaryRepository.save(diary1);
        diaryRepository.save(diary2);

        Question question1 = Question.of("뭘 먹고 싶나요 ? ");
        questionRepository.save(question1);

        Note note1 = CommonNote.of(diary1, "나는 노트 1이다", LocalDate.now(), Visibility.PUBLIC, Feeling.JOY, Weather.SUNNY, Season.WINTER);
        Note note2 = QuestionNote.of(diary1, "나는 노트 2이다", LocalDate.now(), Visibility.PUBLIC, question1, Feeling.JOY, Weather.SUNNY, Season.WINTER);
        Note note3 = RetrospectiveNote.of(diary1, "나는 노트 3", LocalDate.now(), Visibility.PUBLIC, Framework.KTP);
        Note note4 = CommonNote.of(diary2, "나는 노트 4이다", LocalDate.now(), Visibility.PRIVATE, Feeling.JOY, Weather.SUNNY, Season.WINTER);
        Note note5 = CommonNote.of(diary2, "나는 노트 5이다", LocalDate.now(), Visibility.PRIVATE, Feeling.JOY, Weather.SUNNY, Season.WINTER);
        Note note6 = RetrospectiveNote.of(diary2, "나는 노트 6", LocalDate.now(), Visibility.PRIVATE, Framework.KTP);
        Note note7 = CommonNote.of(diary1, "나는 노트 7이다", LocalDate.of(2023,1,1), Visibility.PUBLIC, Feeling.JOY, Weather.SUNNY, Season.WINTER);
        Note note8 = QuestionNote.of(diary1, "나는 노트 8이다", LocalDate.of(2023,1,1), Visibility.PUBLIC, question1, Feeling.JOY, Weather.SUNNY, Season.WINTER);
        Note note9 = RetrospectiveNote.of(diary1, "나는 노트 9", LocalDate.of(2023,1,1), Visibility.PUBLIC, Framework.KTP);
        Note note10 = RetrospectiveNote.of(diary1, "나는 노트 10", LocalDate.of(2023,1,1), Visibility.PRIVATE, Framework.KTP);


        List<String> content0 = new ArrayList<>(Arrays.asList("안먹고 싶어요", "귀찮아요"));
        for (int i = 0; i < content0.size(); i++) {
            NoteContent noteContent = NoteContent.of(i + 1, content0.get(i));
            note2.addContent(noteContent);
        }

        List<String> content = new ArrayList<>(Arrays.asList("붕어빵이 좋아요", "호떡도 좋아요"));
        for (int i = 0; i < content.size(); i++) {
            NoteContent noteContent = NoteContent.of(i + 1, content.get(i));
            note3.addContent(noteContent);
        }

        noteRepository.saveAll(Arrays.asList(note1, note2, note3, note4, note5, note6, note7, note8, note9, note10));
    }
}
