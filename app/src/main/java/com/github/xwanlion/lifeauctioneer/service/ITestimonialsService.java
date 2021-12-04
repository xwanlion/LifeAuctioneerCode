package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.model.app.Testimonials;

import java.util.List;

public interface ITestimonialsService {
    /**
     * save bidder's testimonials to database.
     *
     * @param activityId
     * @param bidderId
     * @return
     */
    public boolean saveTestimonials(int activityId, int bidderId, String content);

    /**
     * delete bidder's testimonials by id
     *
     * @param id
     * @return
     */
    public boolean delTestimonials(int id);

    /**
     * get bidder's testimonials by id.
     * @param id
     * @return
     */
    public Testimonials getTestimonials(int id);

    /**
     * get bidder's testimonials by id.
     * @param activityId
     * @param bidderId
     * @return
     */
    public Testimonials getTestimonials(int activityId, int bidderId);

    /**
     * list all bidder's testimonials which join in auction activity.<br/>
     * @param activityId
     * @return
     */
    public List<Testimonials> listTestimonials(int activityId);

    /**
     * list bidder's money with condition.<br/>
     * @param activityId : activity id.
     * @param pageIndex : the index of the page, begins of 0, default to 0;
     * @param recordsPerPage : how many records each page, default to 20.
     * @return
     */
    public List<Testimonials> listTestimonials(int activityId, int pageIndex, int recordsPerPage);

}
