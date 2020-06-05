package com.vjurkov.courseenrollment.jobs;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//konfiguracijska klasa u springu, spring zna da tu kalsu nece stavit u svoj kontenjer s drugim klasama nego sluši samo
// da konfigurira sam spring
public class SchedulerConfig {

    //da spring zna da je ovo autowired
    @Bean
    public JobDetail coursePrintJobDetail() {
        //kad zatražim dat ces mi ovaj job s ovim nazivom (courseprintjob)
        return JobBuilder.newJob(CoursePrintJob.class).withIdentity("coursePrintJob")
                .storeDurably().build();
    }

    @Bean
    //trigiram taj build
    public SimpleTrigger coursePrintTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(30).repeatForever();

        return TriggerBuilder.newTrigger().forJob(coursePrintJobDetail())
                .withIdentity("coursePrintTrigger").withSchedule(scheduleBuilder).build();
    }

}
