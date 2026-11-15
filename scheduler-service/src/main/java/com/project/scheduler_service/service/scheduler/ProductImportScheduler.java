package com.project.scheduler_service.service.scheduler;

import com.project.scheduler_service.service.ImportSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductImportScheduler {

    private final ImportSchedulerService importSchedulerService;

    @EventListener(ApplicationReadyEvent.class)
    public void runInitialImport() {
        log.info("[Scheduler] Aplikasi siap - Menjalankan impor awal otomatis...");
        executeSafely();
    }

    @Scheduled(cron = "${scheduler.import.cron}")
    public void runScheduledImport() {
        log.info("[Scheduler] Menjalankan impor terjadwal (setiap 2 menit)...");
        executeSafely();
    }

    private void executeSafely() {
        try {
            importSchedulerService.doImport();
        } catch (Exception e) {
            log.error("[Scheduler] Gagal melakukan impor produk (service tetap berjalan untuk putaran berikutnya): {}", e.getMessage());
        }
    }
}