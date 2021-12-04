package com.github.xwanlion.lifeauctioneer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.xwanlion.lifeauctioneer.model.app.Testimonials;

import java.util.List;

@Dao
public interface TestimonialsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveTestimonials(Testimonials testimonials);

    @Delete
    public int delTestimonials(Testimonials testimonials);

    @Query("select id, activity_id, bidder_id, content from testimonials where id = :id")
    public Testimonials getTestimonials(int id);

    @Query("select id, activity_id, bidder_id, content from testimonials where activity_id = :activityId and bidder_id = :bidderId")
    public Testimonials getTestimonials(int activityId, int bidderId);

    @Query("select id, activity_id, bidder_id, content from testimonials where activity_id = :activityId")
    public List<Testimonials> listTestimonials(int activityId);

    @Query("select id, activity_id, bidder_id, content from testimonials where activity_id = :activityId order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<Testimonials> listTestimonials(int activityId, int pageIndex, int recordsPerPage);

}
