package mizdooni.utils;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;

public final class MizdooniMetrics {
    private static final LongCounter requestCounter = GlobalOpenTelemetry
            .getMeter("Mizdooni")
            .counterBuilder("total_requests_counter")
            .setDescription("Total number of requests")
            .build();

    private static final LongCounter okResponseCounter = GlobalOpenTelemetry
            .getMeter("Mizdooni")
            .counterBuilder("ok_responses_counter")
            .setDescription("Total number of OK responses")
            .build();

    private static final LongCounter signupCounter = GlobalOpenTelemetry
            .getMeter("Mizdooni")
            .counterBuilder("signup_counter")
            .setDescription("Total number of new user signups")
            .build();

    private static final LongCounter reservationCounter = GlobalOpenTelemetry
            .getMeter("Mizdooni")
            .counterBuilder("reservation_counter")
            .setDescription("Total number of successful reservations")
            .build();

    private static final LongCounter cancellationCounter = GlobalOpenTelemetry
            .getMeter("Mizdooni")
            .counterBuilder("cancellation_counter")
            .setDescription("Total number of reservation cancellations")
            .build();

    private MizdooniMetrics() {

    }

    public static void newRequest() {
        requestCounter.add(1);
    }

    public static void newOkResponse() {
        okResponseCounter.add(1);
    }

    public static void newSignup() {
        signupCounter.add(1);
    }

    public static void newReservation() {
        reservationCounter.add(1);
    }

    public static void newCancellation() {
        cancellationCounter.add(1);
    }
}
