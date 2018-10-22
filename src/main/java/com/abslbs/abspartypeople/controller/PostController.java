package com.abslbs.abspartypeople.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.abslbs.abspartypeople.domain.Attachment;
import com.abslbs.abspartypeople.domain.Post;
import com.abslbs.abspartypeople.domain.Rating;
import com.abslbs.abspartypeople.domain.User;
import com.abslbs.abspartypeople.repository.AttachmentRepository;
import com.abslbs.abspartypeople.repository.PostRepository;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	PostRepository postRepository;

	@Autowired
	AttachmentRepository attachmentRepository;

	@GetMapping("/all")
	public String allPosts(Model model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		model.addAttribute("user", (User) session.getAttribute("user"));
		model.addAttribute("admin",
				"Admin".equals(((User) session.getAttribute("user")).getRole().getName()) ? "1" : "0");
		model.addAttribute("postList", postRepository.findAllInReverseChronologicalOrder());
		model.addAttribute("post", new Post());
		return "allPosts";
	}

	@GetMapping(value = "/displayAttachment")
	public void displayAttachment(@RequestParam("id") Long attachmentId, HttpServletResponse response,
			HttpServletRequest request) throws ServletException, IOException {
		Optional<Attachment> attachment = attachmentRepository.findById(attachmentId);
		if (attachment.isPresent()) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(attachment.get().getContent());
			response.getOutputStream().close();
		}
	}

	@PostMapping("/add")
	public RedirectView addPost(@ModelAttribute Post post, @RequestParam("attachment") MultipartFile uploadingFile)
			throws IOException {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		User user = (User) session.getAttribute("user");
		post.setUser(user);
		post.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		List<Attachment> attachmentList = new ArrayList<>();
		Attachment attachment = new Attachment();
		attachment.setContent(uploadingFile.getBytes());
		attachment.setPost(post);
		attachmentList.add(attachment);
		post.setAttachmentList(attachmentList);
		List<Rating> ratingList = new ArrayList<>();
		post.setRatingList(ratingList);
		postRepository.save(post);
		return new RedirectView("all");
	}

}
