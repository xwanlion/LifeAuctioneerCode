package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.dao.TestimonialsDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Testimonials;
import java.util.List;

public class TestimonialsServiceImpl implements ITestimonialsService {

    private IAuctionsService activityService = null;
    private TestimonialsDao testimonialsDao = null;

    public TestimonialsServiceImpl() {
        activityService = new AuctionsServiceImpl();
        IDatabase database = IDatabase.getDatabase();
        testimonialsDao = database.getTestimonialsDao();
    }

    @Override
    public boolean saveTestimonials(int activityId, int bidderId, String content) {
        if (activityId <= 0) return false;
        if (bidderId <= 0) return false;

        Auctions auctions = activityService.getActivity(activityId);
        if (auctions == null) throw new RuntimeException("ERR_INVALID_ACTIVITY_ID");

        Testimonials testimonials = this.getTestimonials(activityId, bidderId);
        if (testimonials == null) testimonials = new Testimonials();
        testimonials.setActivityId(activityId);
        testimonials.setBidderId(bidderId);
        testimonials.setContent(content);

        long id = testimonialsDao.saveTestimonials(testimonials);
        return id > 0;

    }

    @Override
    public boolean delTestimonials(int id) {
        Testimonials testimonials = new Testimonials();
        testimonials.setId(id);
        long r = testimonialsDao.delTestimonials(testimonials);
        return r > 0;
    }

    @Override
    public Testimonials getTestimonials(int id) {
        return testimonialsDao.getTestimonials(id);
    }

    @Override
    public Testimonials getTestimonials(int activityId, int bidderId) {
        return testimonialsDao.getTestimonials(activityId, bidderId);
    }

    @Override
    public List<Testimonials> listTestimonials(int activityId) {
        return testimonialsDao.listTestimonials(activityId);
    }

    @Override
    public List<Testimonials> listTestimonials(int activityId, int pageIndex, int recordsPerPage) {
        if (pageIndex <= 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return testimonialsDao.listTestimonials(activityId, pageIndex, recordsPerPage);
    }

}
