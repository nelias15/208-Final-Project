
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CoursePath registration eligibility checker.
 *
 * Eligible = P && S && !H && D
 *
 * P = prerequisite completed
 * S = seat available
 * H = registration hold exists
 * D = deadline has not passed
 */
public class LaptopWise {

    public enum Status {
        READY_TO_REGISTER,
        RESOLVE_HOLD,
        CHOOSE_ANOTHER_SECTION,
        CHECK_PREREQUISITE,
        REGISTRATION_CLOSED
    }

    public static class RegistrationResult {
        private final boolean eligible;
        private final Status status;
        private final List<String> explanations;

        public RegistrationResult(boolean eligible, Status status,
                                  List<String> explanations) {
            this.eligible = eligible;
            this.status = status;
            this.explanations = Collections.unmodifiableList(
                    new ArrayList<>(explanations));
        }

        public boolean isEligible() {
            return eligible;
        }

        public Status getStatus() {
            return status;
        }

        public String getStatusMessage() {
            switch (status) {
                case READY_TO_REGISTER:
                    return "Ready to Register";
                case RESOLVE_HOLD:
                    return "Resolve a Hold";
                case CHOOSE_ANOTHER_SECTION:
                    return "Choose Another Section";
                case CHECK_PREREQUISITE:
                    return "Check Prerequisite";
                case REGISTRATION_CLOSED:
                    return "Registration Closed";
                default:
                    return "Unknown";
            }
        }

        public List<String> getExplanations() {
            return explanations;
        }
    }

    public RegistrationResult evaluate(
            LaptopCatalog.Course course,
            boolean prerequisiteCompleted,
            boolean registrationHold,
            LocalDate registrationDeadline,
            LocalDate today) {

        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (registrationDeadline == null || today == null) {
            throw new IllegalArgumentException("Dates cannot be null.");
        }

        boolean seatAvailable = course.isSeatAvailable();
        boolean deadlineOpen = !today.isAfter(registrationDeadline);

        boolean eligible = prerequisiteCompleted
                && seatAvailable
                && !registrationHold
                && deadlineOpen;

        List<String> explanations = new ArrayList<>();

        explanations.add(prerequisiteCompleted
                ? "Prerequisite requirement is satisfied."
                : "Prerequisite has not been completed.");

        explanations.add(seatAvailable
                ? "A seat is available."
                : "No seats are currently available.");

        explanations.add(registrationHold
                ? "A registration hold exists."
                : "No registration hold exists.");

        explanations.add(deadlineOpen
                ? "The registration deadline has not passed."
                : "The registration deadline has passed.");

        Status status;

        if (eligible) {
            status = Status.READY_TO_REGISTER;
        } else if (!prerequisiteCompleted) {
            status = Status.CHECK_PREREQUISITE;
        } else if (registrationHold) {
            status = Status.RESOLVE_HOLD;
        } else if (!seatAvailable) {
            status = Status.CHOOSE_ANOTHER_SECTION;
        } else {
            status = Status.REGISTRATION_CLOSED;
        }

        return new RegistrationResult(eligible, status, explanations);
    }

    public RegistrationResult evaluate(
            String courseCode,
            LaptopCatalog catalog,
            boolean prerequisiteCompleted,
            boolean registrationHold,
            LocalDate registrationDeadline,
            LocalDate today) {

        if (catalog == null) {
            throw new IllegalArgumentException("Catalog cannot be null.");
        }

        LaptopCatalog.Course course = catalog.findCourse(courseCode);

        if (course == null) {
            throw new IllegalArgumentException(
                    "Course not found: " + courseCode);
        }

        return evaluate(course, prerequisiteCompleted,
                registrationHold, registrationDeadline, today);
    }
}

