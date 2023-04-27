package com.courses.server.services.impl;

import com.courses.server.dto.request.FeedbackRequest;
import com.courses.server.entity.*;
import com.courses.server.entity.Class;
import com.courses.server.entity.Package;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.*;
import com.courses.server.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(FeedbackRequest req) {
        if (req.getBody() == null)
            throw new BadRequestException(400, "Please input comment");
        Feedback feedback = new Feedback();
        if (req.getVote() >= 1 && req.getVote() <= 5)
            feedback.setVote(req.getVote());

        feedback.setBody(req.getBody());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        feedback.setUser(user);

        if (req.getExpertId() != null) {
            Expert expert = null;
            try {
                expert = expertRepository.findById(req.getExpertId()).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (expert == null)
                throw new NotFoundException(404, "Expert  Không tồn tại");
            feedback.setExpert(expert);
            feedback.setStatus(1);
        } else if (req.getPackageId() != null) {
            Package _package = null;
            try {
                _package = packageRepository.findById(req.getPackageId()).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (_package == null)
                throw new NotFoundException(404, "Package  Không tồn tại");
            feedback.setAPackage(_package);
            feedback.setStatus(2);
        } else if (req.getClassId() != null) {
            Class classes = null;
            try {
                classes = classRepository.findById(req.getClassId()).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (classes == null)
                throw new NotFoundException(404, "Class  Không tồn tại");
            feedback.setAclass(classes);
            feedback.setStatus(4);
        } else if (req.getBlogId() != null) {
            Post blog = null;
            try {
                blog = postRepository.findById(req.getBlogId()).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (blog == null)
                throw new NotFoundException(404, "Blog  Không tồn tại");
            feedback.setPost(blog);
            feedback.setStatus(5);
        } else if (req.getComboId() != null) {
            Combo combo = null;
            try {
                combo = comboRepository.findById(req.getComboId()).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (combo == null)
                throw new NotFoundException(404, "Combo  Không tồn tại");
            feedback.setCombo(combo);
            feedback.setStatus(3);
        }

        feedbackRepository.save(feedback);
    }

    @Override
    public void createAdmin(FeedbackRequest req) {
        if (req.getBody() == null)
            throw new BadRequestException(400, "Vui lòng nhập bình luận");
        if (req.getEmail() == null)
            throw new BadRequestException(400, "Vui lòng nhập email");
        Feedback feedback = new Feedback();
        if (req.getVote() >= 1 && req.getVote() <= 5)
            feedback.setVote(req.getVote());
        User user = userRepository.findByEmail(req.getEmail()).orElse(null);
        if (user == null)
            throw new BadRequestException(400, "Người dùng không tồn tại");
        feedback.setBody(req.getBody());
        feedback.setUser(user);
        feedback.setStatus(0);
        feedbackRepository.save(feedback);
    }

    @Override
    public void update(Long id, FeedbackRequest req) {

        Feedback feedback = null;
        try {
            feedback = feedbackRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (feedback == null)
            throw new NotFoundException(404, "Phản hồi Không tồn tại");

        if (req.getBody() != null)
            feedback.setBody(req.getBody());
        if (req.getEmail() != null) {
            User user = userRepository.findByEmail(req.getEmail()).orElse(null);
            if (user == null)
                throw new BadRequestException(400, "Người dùng không tồn tại");
            feedback.setUser(user);
        }
        if (req.getVote() >= 1 && req.getVote() <= 5)
            feedback.setVote(req.getVote());

        feedbackRepository.save(feedback);
    }

    @Override
    public void delete(Long id) {
        Feedback feedback = null;
        try {
            feedback = feedbackRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (feedback == null)
            throw new NotFoundException(404, "Feedback  Không tồn tại");

        feedbackRepository.delete(feedback);
    }

    @Override
    public Page<Feedback> getList(int status, long info, Pageable pageable) {
        if (status == 1) {
            return feedbackRepository.getListFeedbackExpert(info, pageable);
        } else if (status == 2) {
            return feedbackRepository.getListFeedbackPackage(info, pageable);
        } else if (status == 3) {
            return feedbackRepository.getListFeedbackCombo(info, pageable);
        } else if (status == 4) {
            return feedbackRepository.getListFeedbackClass(info, pageable);
        } else if (status == 5) {
            return feedbackRepository.getListFeedbackPost(info, pageable);
        } else {
            return feedbackRepository.findAllByStatusOrderByCreatedDateDesc(0, pageable);
        }
    }

    @Override
    public Feedback getDetail(Long id) {
        Feedback feedback = null;
        try {
            feedback = feedbackRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (feedback == null)
            throw new NotFoundException(404, "Feedback  Không tồn tại");

        return feedback;
    }
}
