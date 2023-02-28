package ChamStudy.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.VideoDto;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Entity.ContentVideo;
import ChamStudy.Entity.StudyHistory;
import ChamStudy.Entity.UserInfo;
import ChamStudy.Repository.OnContentRepository;
import ChamStudy.Repository.OnContentVideoRepository;
import ChamStudy.Repository.StudyHistortRepository;
import ChamStudy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentVideoService {
	
    private final OnContentVideoRepository contentVideoRepository;
	private final StudyHistortRepository studyHistortRepository;
	private final OnContentRepository onContentRepository;
	private final UserRepository userRepository;
	
	public void insertVideoData(ContentInfo contentId, int count) {
        for (int i = 1; i <= count; i++) {
            ContentVideo contentVideo = new ContentVideo();
            contentVideo.setName(contentId.getId()+"-" + i);
            contentVideo.setUrl("/video/"+contentId.getId()+"/"+contentId.getId()+"-" + i);
            contentVideo.setContentInfo(contentId);
            contentVideoRepository.save(contentVideo);
        }
    }
	
	public List<ContentVideo> videoList(ContentInfo contentInfo){
		return contentVideoRepository.findByContentInfoOrderById(contentInfo);
	}
	
	public Long getContentId(String videoName) {
		return contentVideoRepository.getContentId(videoName);
	}
	
	public String getContentUrl(String videoUrl) {
		return contentVideoRepository.getContentUrl(videoUrl);
	}
	
	public void createStudyHistory(String videoName, Long contentId, String email) {
		ContentVideo videoId = contentVideoRepository.getId(videoName);
		ContentInfo getContentId = onContentRepository.getContentId(contentId);
		Long history_id = studyHistortRepository.getVideoId(videoId.getId());
		UserInfo userId = userRepository.getUserId(email);
		StudyHistory studyHistory = new StudyHistory();
		if(history_id == null) {
			studyHistory.setVideoId(videoId);
			studyHistory.setContentId(getContentId);
			studyHistory.setUserId(userId);
		} else {
			studyHistory.setVideoId(videoId);
			studyHistory.setContentId(getContentId);
			studyHistory.setId(history_id);
			studyHistory.setUserId(userId);
		}
		studyHistortRepository.save(studyHistory);
	}
	
	public Long getProcess(Long contentId) {
		return studyHistortRepository.getCountVideoId(contentId);
	}
	
}
