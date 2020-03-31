package com.vjurkov.courseenrollment.jobs;

import com.vjurkov.courseenrollment.dao.CourseRepository;
import com.vjurkov.courseenrollment.model.Course;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
//spring ima u pozadini object pulling
//ova klasa radi job - pita repozitori da da sve korsove i ispise ih na ekran
//jobovi se koriste samo za interne stvari - npr job koji na kraju mjeseca izračunava neke podatke, nešto napravi s podacima
public class CoursePrintJob extends QuartzJobBean {

    private Logger log = LoggerFactory.getLogger(CoursePrintJob.class);

    private final CourseRepository courseRepository;

    public CoursePrintJob(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Iterable<Course> exercises = courseRepository.findAll();

        if(exercises.iterator().hasNext()) {
            log.info("These are the courses currently entered in the app");
            exercises.forEach(it ->
                    log.info(it.getName())
            );
        } else {
            log.info("These are currently no courses in the app");
        }
    }



}
