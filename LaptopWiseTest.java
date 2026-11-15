
import java.time.LocalDate;

/**
 * Tests the CoursePath registration system.
 */
public class LaptopWiseTest {

    private static int testsPassed = 0;

    public static void main(String[] args) {

        LaptopCatalog catalog =
                LaptopCatalog.createSampleCatalog();

        LaptopWise registration = new LaptopWise();

        LocalDate today = LocalDate.of(2026, 11, 14);
        LocalDate deadline = LocalDate.of(2026, 11, 15);

        LaptopCatalog.Course course =
                catalog.findCourse("COSC 208");

        // Test 1: All conditions satisfied
        LaptopWise.RegistrationResult result =
                registration.evaluate(
                        course, true, false, deadline, today);

        check(result.isEligible(),
                "Student should be eligible.");

        check(result.getStatus()
                        == LaptopWise.Status.READY_TO_REGISTER,
                "Expected Ready to Register.");

        // Test 2: Missing prerequisite
        result = registration.evaluate(
                course, false, false, deadline, today);

        check(!result.isEligible(),
                "Missing prerequisite blocks registration.");

        check(result.getStatus()
                        == LaptopWise.Status.CHECK_PREREQUISITE,
                "Expected Check Prerequisite.");

        // Test 3: Registration hold
        result = registration.evaluate(
                course, true, true, deadline, today);

        check(!result.isEligible(),
                "A hold should block registration.");

        check(result.getStatus()
                        == LaptopWise.Status.RESOLVE_HOLD,
                "Expected Resolve a Hold.");

        // Test 4: No seats available
        LaptopCatalog.Course fullCourse =
                catalog.findCourse("COSC 301");

        result = registration.evaluate(
                fullCourse, true, false, deadline, today);

        check(!result.isEligible(),
                "A full course should block registration.");

        check(result.getStatus()
                        == LaptopWise.Status.CHOOSE_ANOTHER_SECTION,
                "Expected Choose Another Section.");

        // Test 5: Deadline has passed
        result = registration.evaluate(
                course, true, false, today.minusDays(1), today);

        check(!result.isEligible(),
                "Expired deadline blocks registration.");

        check(result.getStatus()
                        == LaptopWise.Status.REGISTRATION_CLOSED,
                "Expected Registration Closed.");

        // Test 6: Deadline is today
        result = registration.evaluate(
                course, true, false, today, today);

        check(result.isEligible(),
                "Registration is open on the deadline date.");

        // Test 7: Changing a hold changes eligibility
        result = registration.evaluate(
                course, true, true, deadline, today);

        check(!result.isEligible(),
                "Adding a hold changes eligibility.");

        // Test 8: Course catalog
        check(catalog.findCourse("COSC 208") != null,
                "Course should exist.");

        check(catalog.findCourse("UNKNOWN") == null,
                "Unknown course should not exist.");

        check(catalog.size() == 3,
                "Catalog should contain three courses.");

        System.out.println(
                "All " + testsPassed + " tests passed!");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("FAILED: " + message);
        }

        testsPassed++;
        System.out.println("PASS: " + message);
    }
}
