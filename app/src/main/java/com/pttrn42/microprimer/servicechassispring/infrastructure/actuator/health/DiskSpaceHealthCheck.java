package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator.health;

import com.codahale.metrics.health.HealthCheck;
import org.springframework.util.unit.DataSize;

import java.io.File;

public class DiskSpaceHealthCheck extends HealthCheck {
    private final File path;
    private final DataSize threshold;

    public DiskSpaceHealthCheck(File path, DataSize threshold) {
        this.path = path;
        this.threshold = threshold;
    }

    @Override
    protected Result check() throws Exception {
        long diskFreeInBytes = this.path.getUsableSpace();

        ResultBuilder builder = Result.builder()
                .withDetail("total", this.path.getTotalSpace())
                .withDetail("free", diskFreeInBytes)
                .withDetail("threshold", this.threshold.toBytes())
                .withDetail("exists", this.path.exists());
        if (diskFreeInBytes >= this.threshold.toBytes()) {
            return builder.healthy().build();
        } else {
            String message = String.format("Free disk space below threshold. Available: %d bytes (threshold: %s)", diskFreeInBytes, this.threshold);
            return builder.unhealthy()
                .withMessage(message).build();
        }
    }
}