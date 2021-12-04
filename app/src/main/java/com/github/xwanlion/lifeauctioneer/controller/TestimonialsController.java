package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Testimonials;
import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api/testimonials")
public class TestimonialsController {

    @ResponseBody
    @PostMapping(path = "/new")
    public Map<String, Object> newTestimonials (
        @RequestParam(name = "activityId", required = true) int activityId,
        @RequestParam(name = "bidderId", required = true) int bidderId,
        @RequestParam(name = "content", required = true) String content
    ) {
        Auctions auctions = BeanManager.auctionsService.getCurrentActivity();
        if (activityId != auctions.getId()) return Msg.error("ERR_INVALID_ACTIVITY_ID");

        boolean success = BeanManager.testimonialsService.saveTestimonials(activityId, bidderId, content);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/get/{id}")
    public Map<String, Object> getTestimonials (@PathVariable("id") int id) {
        Testimonials testimonials = BeanManager.testimonialsService.getTestimonials(id);
        if (testimonials == null) return Msg.success("data", "{}");
        return Msg.success("data", Testimonials.toMap(testimonials));

    }

    @ResponseBody
    @GetMapping(path = "/get/{activityId}/{bidderId}")
    public Map<String, Object> getTestimonials (
        @PathVariable("activityId") int activityId,
        @PathVariable("activityId") int bidderId
    ) {
        Testimonials testimonials = BeanManager.testimonialsService.getTestimonials(activityId, bidderId);
        if (testimonials == null) return Msg.success("data", "{}");
        return Msg.success("data", Testimonials.toMap(testimonials));
    }

    @ResponseBody
    @GetMapping(path = "/list/{activityId}")
    public Map<String, Object> listTestimonials (@PathVariable("activityId") int activityId) {
        List<Testimonials> list = BeanManager.testimonialsService.listTestimonials(activityId);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        List<Map<String, Object>> xList = Testimonials.toMap(list);
        return Msg.success("data", xList);
    }

    @ResponseBody
    @GetMapping(path = "/list/{pageIndex}/{recordsPerPage}")
    public Map<String, Object> listTestimonials (
        @PathVariable("activityId") int activityId,
        @PathVariable("pageIndex") int pageIndex,
        @PathVariable("recordsPerPage") int recordsPerPage
    ) {
        List<Testimonials> list = BeanManager.testimonialsService.listTestimonials(activityId, pageIndex, recordsPerPage);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        List<Map<String, Object>> xList = Testimonials.toMap(list);
        return Msg.success("data", xList);
    }

    @ResponseBody
    @GetMapping(path = "/del/{id}")
    public Map<String, Object> delTestimonials (@PathVariable("id") int id) {
        boolean success = BeanManager.testimonialsService.delTestimonials(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();
    }

}
