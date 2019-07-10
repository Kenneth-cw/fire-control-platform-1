package com.shine.iot.signal.reader.quartz;


import com.shine.iot.signal.reader.config.LoraLiteYmlReadUtils;
import com.shine.iot.signal.reader.quartz.job.DownAckJob;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 回复ACK的 延时任务安排
 */
public final class IotScheduleServer {
    private Logger logger = LogManager.getLogger(IotScheduleServer.class);
    @Singleton
    private static Scheduler sched;

    private static IotScheduleServer instance = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    private int ackCnt = 1;
    private int ack_delayed = 1500;

    private IotScheduleServer() {
        SchedulerFactory sf = new StdSchedulerFactory();  //SchedulerFactory 是创建任务调度器的工厂类（接口） ，StdSchedulerFactory 是它的实现类（实现通知工厂类创建任务调度器实例）
        try {
            sched = sf.getScheduler();  //通过工厂类   获取一个任务调度器实例
            sched.start();  //启动该任务调度器

            String _ack_delayed = (String) LoraLiteYmlReadUtils.getCommonYml("loralite.netserver.ipaddr");
            if (StringUtils.isNotBlank(_ack_delayed) && NumberUtils.isDigits(_ack_delayed)) {
                ack_delayed = Integer.parseInt(_ack_delayed);
            }
        } catch (SchedulerException e) {
            logger.error("ack 定时器启动失败！");
            logger.error(e.getMessage(), e);
        }
    }

    public static synchronized IotScheduleServer getInstance() {
        if (instance == null) {
            instance = new IotScheduleServer();
        }
        return instance;
    }

    /**
     * @param ackDataMap
     */
    public void addLoraLiteAckTask(HashMap<String, Object> ackDataMap) {
        JobDataMap ackData = new JobDataMap();
        ackData.putAll(ackDataMap);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + ack_delayed);//.add(Calendar.MILLISECOND, ack_send_interval);//1.2s后发送ack信息
        Date startTime = calendar.getTime();//DateBuilder.nextGivenSecondDate(null,1);
        JobDetail jobDetail = JobBuilder.newJob(DownAckJob.class)
                .usingJobData(ackData)
                .withIdentity("ackId" + ackCnt, "ackJobGroup")
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("ackT" + ackCnt, "ackTrgGroup")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();

        ackCnt++;
        try {
            logger.debug("ack job:[ackId" + ackCnt + "] scheduled at : " + sdf.format(Calendar.getInstance().getTime()));
            sched.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void cleanUp() {
        try {
            if (sched != null && sched.isStarted()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
