package com.github.xwanlion.lifeauctioneer.controller;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestimonialsControllerTester {
    private TestimonialsController testimonialsController = new TestimonialsController();
    private AuctionsController auctionsController = new AuctionsController();

    public void testCreateTestimonials(int activityId, int bidderId, String content) {
        Map<String, Object> rMap = testimonialsController.newTestimonials(activityId, bidderId, content);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);
    }

    public Map<String, Object> testGetTestimonials(int id) {
        Map<String, Object> testimonialsMap = testimonialsController.getTestimonials(id);
        assertEquals(true, (testimonialsMap != null && ((Integer) testimonialsMap.get("code") == 0)));

        Object obj = testimonialsMap.get("data");
        if (obj instanceof String) return null;

        Map<String, Object> testimonialsDataMap = (Map<String, Object>) obj;
        return testimonialsDataMap;

    }

    public Map<String, Object> testGetTestimonials(int activityId, int bidderId) {
        Map<String, Object> testimonialsMap = testimonialsController.getTestimonials(activityId, bidderId);
        assertEquals(true, (testimonialsMap != null && ((Integer) testimonialsMap.get("code") == 0)));

        Map<String, Object> testimonialsDataMap = (Map<String, Object>) testimonialsMap.get("data");
        return testimonialsDataMap;

    }

    public List<Map<String, Object>> testListTestimonials(int activityId) {
        Map<String, Object> rMap = testimonialsController.listTestimonials(activityId);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        return list;

    }

    public List<Map<String, Object>> testListTestimonials(int activityId, int pageIndex, int recordsPerPage) {
        Map<String, Object> rMap = testimonialsController.listTestimonials(activityId, pageIndex, recordsPerPage);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        return list;

    }

    public void testDelTestimonials(int id) {
        Map<String, Object> rMap = testimonialsController.delTestimonials(id);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }


    @Test
    public void testTestimonialsAll() {
        int activityId = 1;
        int bidderId = 2;

        auctionsController.newActivity(1, 6000L);
        auctionsController.newActivity(1, 100);

        testCreateTestimonials(activityId, bidderId, "testimonials");

        Map<String, Object> map1 = testGetTestimonials(activityId, bidderId);
        int testimonialsId = (Integer) map1.get("id");
        assertEquals(true, (testimonialsId > 0));

        Map<String, Object> map = testGetTestimonials(testimonialsId);
        assertEquals(true, (map != null && ((Integer) map.get("id") == testimonialsId)));

        List<Map<String, Object>> list = testListTestimonials(activityId);
        assertEquals(true, (list != null && list.size() > 0));

        testDelTestimonials(testimonialsId);
        Map<String, Object> delObjectMap = testGetTestimonials(testimonialsId);
        assertEquals(true, delObjectMap == null);

    }

}
