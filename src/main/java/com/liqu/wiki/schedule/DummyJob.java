package com.liqu.wiki.schedule;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.core.jmx.JobDataMapSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

/**
 * Job 구현체. excute() 메소드에 실행할 작업 내용을 작성
 */
@Configuration
//@Component
@AllArgsConstructor
public class DummyJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(DummyJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("DummyJob execute !!");
		//log.info("context.getJobDetail() : " + context.getJobDetail());
		//log.info("context.getTrigger() : " + context.getTrigger());
		
		// Trigger와 JobDetail에서 설정한 DataMap 출력
 		//context.getMergedJobDataMap().forEach((key, val) -> log.info("key: {} / value: {}", key, val));
	}

	@Bean
	JobDetail dummyJobDetail() {
		return JobBuilder.newJob()
			.ofType(DummyJob.class) 			// 어떤 Job을 실행할 것인지
			.storeDurably() 					// 트리거가 없을 떄에도 Job을 저장
			.withIdentity("dummyJobDetail") 	// 스케줄러가 JobDtail을 식별할 수 있는 Id
			.withDescription("Invoke DummyJob") // Job에 대한 설명
			
			// Job 실행시 전달될 파라미터
			.setJobData(JobDataMapSupport.newJobDataMap(Map.of(
                "param1", "value1",
                "param2", "value2"
			)))
			.usingJobData("param3","value3") 	// DataMap에 데이터 추가
			
		.build();
	}

	/**
	 * @Qualifier("") : JobDetail @Bean의 Object 이름
	 * @param job
	 * @return
	 */
	@Bean
	Trigger dummyJobTrigger(@Qualifier("dummyJobDetail")JobDetail job) {
		return TriggerBuilder.newTrigger()
			.forJob(job) 						// 어떤 job을 실행할지. 메소드 매개변수를 사용.
			.withIdentity("dummyJobTrigger") 	// 스케줄러가 Trigger를 식별할 수 있는 Id
			
			// 간단한 실행 주기 설정
			/*
			.withSchedule( 								
				SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(10)
				.repeatForever()
			)
			*/
			
			// cron 주기 설정
			//.withSchedule(CronScheduleBuilder.cronSchedule("* * * ? * *")) 	// <- 매 초
            .withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * *")) 		// <- 매 분
			
			// .usingJobData(jobDataMap)  // JobDetail 과 마찬가지로 DataMap 세팅 가능
            // 동시에 수행해야 할 Trigger가 여러개 존재할때의 우선순위 지정 
			// .withPriority()
		.build();
	}

}
