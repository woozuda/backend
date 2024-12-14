package com.woozuda.backend.shortlink.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.note.entity.*;
import com.woozuda.backend.note.entity.type.Visibility;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.shortlink.dto.*;
import com.woozuda.backend.shortlink.entity.ShortLink;
import com.woozuda.backend.shortlink.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ShareService {

    private final NoteRepository noteRepository;

    private final UserRepository userRepository;

    private final ShortLinkRepository shortLinkRepository;

    @Transactional
    public void makeSharedNote(@RequestBody NoteIdDto noteIdDto) {

        List<Long> noteId = noteIdDto.getId();
        List<Note> notes = noteRepository.findAllById(noteId);

        //System.out.println(noteId);

        for (Note note : notes) {
            note.setVisibility(Visibility.PUBLIC);
        }

        noteRepository.saveAll(notes);
    }


    @Transactional
    public SharedNoteResponseDto getSharedNote(String username) {

        //
        List<SharedNoteDto> commonNoteResults = noteRepository.searchSharedCommonNote(username);
        List<SharedNoteDto> questionNoteResults = noteRepository.searchSharedQuestionNote(username);
        List<SharedNoteDto> retrospectiveNoteResults = noteRepository.searchSharedRetrospectiveNote(username);

        /*
        List<SharedNoteDto> allSharedNotes = new ArrayList<>();
        allSharedNotes.addAll(commonNoteResults);
        allSharedNotes.addAll(questionNoteResults);
        allSharedNotes.addAll(retrospectiveNoteResults);
        */

        List<SharedNoteTypeDto> allSharedNotes = Stream.concat(
                commonNoteResults.stream().map(note -> new SharedNoteTypeDto("COMMON", note)),
                Stream.concat(
                        questionNoteResults.stream().map(note -> new SharedNoteTypeDto("QUESTION", note)),
                        retrospectiveNoteResults.stream().map(note -> new SharedNoteTypeDto("RETROSPECTIVE", note))
                )
        ).collect(Collectors.toList());
        /*
        for (SharedNoteDto sharedNote : allSharedNotes) {

            if (sharedNote instanceof SharedCommonNoteDto) {
                SharedCommonNoteDto commonNote = (SharedCommonNoteDto) sharedNote;
                System.out.println(commonNote.getTitle());

            }else if (sharedNote instanceof SharedRetrospectiveNoteDto){
                SharedRetrospectiveNoteDto retrospectiveNote = (SharedRetrospectiveNoteDto) sharedNote;
                System.out.println(retrospectiveNote.getType());
                System.out.println(retrospectiveNote.getNoteContents());
            }else if (sharedNote instanceof SharedQuestionNoteDto){
                SharedQuestionNoteDto questionNote = (SharedQuestionNoteDto) sharedNote;
            }
        }
        */

        // 날짜 별로 group by
        Map<LocalDate, List<SharedNoteTypeDto>> allSharedNotesByDate = allSharedNotes.stream()
                .collect(Collectors.groupingBy(dto -> dto.getNote().getDate()));
        /*
        Map<LocalDate, List<SharedNoteDto>> allSharedNotesByDate = allSharedNotes.stream()
                .collect(Collectors.groupingBy(SharedNoteDto::getDate));
        */
        List<SharedNoteByDateDto> sharedNotesByDateDtoList = allSharedNotesByDate.entrySet().stream()
                .map(entry -> new SharedNoteByDateDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // totalCount 반영

        return new SharedNoteResponseDto(allSharedNotes.size(), sharedNotesByDateDtoList);

    }

    public List<String> extractContent(List<NoteContent> notecontents){

        return null;
    }

    @Transactional
    public ShortLinkDto makeShortLink(String username){

        UserEntity userEntity = userRepository.findByUsername(username);

        //이미 해당 계정에는 숏링크가 존재함 . 예외처리
        if(shortLinkRepository.findByUserEntity(userEntity) != null){
            return null;
        }

        String newShortLink = "";

        while(true){

            // 숏링크 생성
            newShortLink = createRandomLink();

            // 중복 검사 (다른 유저랑 동등한 숏링크 인지?)
            if(shortLinkRepository.findByUrl(newShortLink) == null){
                break;
            }
        }

        ShortLink shortLink = new ShortLink(null, newShortLink, userEntity);

        shortLinkRepository.save(shortLink);

        return new ShortLinkDto(newShortLink);
    }

    public String createRandomLink(){

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int strLength = str.length();

        Random random = new Random();

        StringBuffer randomStr = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            randomStr.append(str.charAt(random.nextInt(strLength)));
        }

        return randomStr.toString();
    }

    @Transactional
    public ShortLinkDto getShortLink(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        //이미 해당 계정에는 숏링크가 존재함 . 예외처리
        ShortLink shortlink = shortLinkRepository.findByUserEntity(userEntity);

        if(shortlink != null){
            return new ShortLinkDto(shortlink.getUrl());
        }else{
            return new ShortLinkDto(null);
        }
    }

    public String getUsername(String hashcode){
        ShortLink shortLink = shortLinkRepository.findByUrl(hashcode);
        return shortLink.getUserEntity().getUsername();
    }



}
