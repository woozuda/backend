package com.woozuda.backend.shortlink.Service;


//import com.woozuda.backend.image.dto.ImageDto;
import com.woozuda.backend.note.entity.Note;
import com.woozuda.backend.note.entity.type.Visibility;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.shortlink.dto.NoteIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShareService {

    private final NoteRepository noteRepository;

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

}
