package com.project.product_service.service.scheduler;

import com.project.product_service.dto.response.ReportResponseDto;
import com.project.product_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductReportScheduler {

    private final ReportService reportService;

    @Scheduled(cron = "${report.scheduler.cron}")
    public void runScheduledReport() {
        log.info("[ReportScheduler] Mulai membuat laporan produk CSV terjadwal...");
        try {
            ReportResponseDto report = reportService.generateReport();
            log.info("[ReportScheduler] Laporan selesai: {} ({} data)", report.getFileName(), report.getTotalData());
        } catch (Exception e) {
            log.error("[ReportScheduler] Gagal membuat laporan CSV: {}", e.getMessage());
        }
    }
}