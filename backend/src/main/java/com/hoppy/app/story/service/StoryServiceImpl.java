package com.hoppy.app.story.service;

import com.hoppy.app.like.domain.MemberStoryLike;
import com.hoppy.app.like.repository.MemberStoryLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.domain.story.StoryReply;
import com.hoppy.app.story.dto.PagingStoryDto;
import com.hoppy.app.story.dto.StoryDetailDto;
import com.hoppy.app.story.dto.SaveStoryDto;
import com.hoppy.app.story.dto.StoryDto;
import com.hoppy.app.story.dto.StoryReplyRequestDto;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.repository.StoryReplyRepository;
import com.hoppy.app.story.repository.StoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    private final MemberStoryLikeRepository memberStoryLikeRepository;

    private final MemberService memberService;

    @Override
    public Story findByStoryId(Long storyId) {
        Optional<Story> optStory = storyRepository.findById(storyId);
        if(optStory.isEmpty()) {
            throw new BusinessException(ErrorCode.STORY_NOT_FOUND);
        }
        return optStory.get();
    }


    @Override
    public void saveStory(Story story, Member member) {
        storyRepository.save(story);
    }

    @Override
    public Story uploadStory(UploadStoryDto dto, Member member) {
        return Story.of(dto, member);
    }

    @Override
    public Story updateStory(UploadStoryDto dto, Long storyId) {
        Optional<Story> optStory = storyRepository.findById(storyId);
        if(optStory.isEmpty()) {
            throw new BusinessException(ErrorCode.STORY_NOT_FOUND);
        }
        optStory.ifPresent(selectStory -> {
            selectStory.setTitle(dto.getTitle());
            selectStory.setContent(dto.getContent());
            selectStory.setFilePath(dto.getFilename());
            storyRepository.save(selectStory);
        });
        return optStory.get();
    }

    @Override
    public void deleteStory(Long storyId) {
        Optional<Story> optStory = storyRepository.findById(storyId);
        if(optStory.isEmpty()) {
            throw new BusinessException(ErrorCode.STORY_NOT_FOUND);
        }
        storyRepository.deleteById(storyId);
    }

    @Override
    public List<SaveStoryDto> showMyStoriesInProfile(Member member) {
        List<Story> stories = storyRepository.findByMemberIdOrderByIdDesc(member.getId());
        return stories.stream().map(story -> SaveStoryDto.of(story, member)).collect(
                Collectors.toList());
    }


    @Override
    public PagingStoryDto pagingStory(Long lastId) {
        lastId = validCheckLastId(lastId);
        List<Story> storyList = storyRepository.findNextStoryOrderByIdDesc(lastId, PageRequest.of(0, 3));
        if(storyList.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_MORE_STORY);
        }
        lastId = getLastId(storyList);
        String nextPageUrl = getNextPagingUrl(lastId);
        List<StoryDto> storyDtoList = listToDtoList(storyList);

        return PagingStoryDto.of(storyDtoList, nextPageUrl);
    }

    public List<StoryDto> listToDtoList(List<Story> storyList) {
        return storyList.stream().map(StoryDto::of).collect(Collectors.toList());
    }

    public Long validCheckLastId(Long lastId) {
        return (lastId == 0) ? Long.MAX_VALUE : lastId;
    }

    public long getLastId(List<Story> storyList) {
        return storyList.get(storyList.size() - 1).getId();
    }

    public String getNextPagingUrl(Long lastId) {
        if(lastId >= 0) {
            return "https://hoppy.kro.kr/api/story?lastId=" + lastId;
        } else {
            return "end";
        }
    }

    @Override
    @Transactional
    public void likeStory(Long memberId, Long storyId) {
        Optional<MemberStoryLike> likeOptional = memberStoryLikeRepository.findByMemberIdAndStoryId(memberId, storyId);

        // TODO: 이미 좋아요를 누른 상태라면 '좋아요 취소' 기능 구현 필요
        // TODO: 단, 좋아요 광클 시 데이터 처리를 고려해야함
        if (likeOptional.isPresent()) {
            return;
        }
        Member member = memberService.findById(memberId);
        Story story = findByStoryId(storyId);
        memberStoryLikeRepository.save(MemberStoryLike.of(member, story));
    }

    @Override
    @Transactional
    public void dislikeStory(Long memberId, Long storyId) {
        memberStoryLikeRepository.deleteByMemberIdAndStoryId(memberId, storyId);
    }
}
