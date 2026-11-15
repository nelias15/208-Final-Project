
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stores courses and checks available seats.
 */
public class LaptopCatalog {

    public static class Course {
        private final String courseCode;
        private final String title;
        private final String prerequisiteCode;
        private final int capacity;
        private final int enrolled;

        public Course(String courseCode, String title,
                      String prerequisiteCode,
                      int capacity, int enrolled) {

            if (courseCode == null || courseCode.isBlank()) {
                throw new IllegalArgumentException(
                        "Course code is required.");
            }

            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException(
                        "Course title is required.");
            }

            if (capacity < 0 || enrolled < 0 || enrolled > capacity) {
                throw new IllegalArgumentException(
                        "Invalid enrollment or capacity.");
            }

            this.courseCode = courseCode;
            this.title = title;
            this.prerequisiteCode = prerequisiteCode;
            this.capacity = capacity;
            this.enrolled = enrolled;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getTitle() {
            return title;
        }

        public String getPrerequisiteCode() {
            return prerequisiteCode;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getEnrolled() {
            return enrolled;
        }

        public int getAvailableSeats() {
            return capacity - enrolled;
        }

        public boolean isSeatAvailable() {
            return getAvailableSeats() > 0;
        }

        @Override
        public String toString() {
            return courseCode + " - " + title
                    + " | Prerequisite: "
                    + (prerequisiteCode == null
                    || prerequisiteCode.isBlank()
                    ? "None" : prerequisiteCode)
                    + " | Available seats: " + getAvailableSeats();
        }
    }

    private final Map<String, Course> courses =
            new LinkedHashMap<>();

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException(
                    "Course cannot be null.");
        }

        String key = course.getCourseCode().toUpperCase();

        if (courses.containsKey(key)) {
            throw new IllegalArgumentException(
                    "Course already exists: " + key);
        }

        courses.put(key, course);
    }

    public Course findCourse(String courseCode) {
        if (courseCode == null) {
            return null;
        }

        return courses.get(courseCode.toUpperCase());
    }

    public Collection<Course> getAllCourses() {
        return Collections.unmodifiableCollection(courses.values());
    }

    public int size() {
        return courses.size();
    }

    public static LaptopCatalog createSampleCatalog() {
        LaptopCatalog catalog = new LaptopCatalog();

        catalog.addCourse(new Course(
                "COSC 208",
                "Discrete Structures",
                "COSC 202",
                30,
                18));

        catalog.addCourse(new Course(
                "COSC 301",
                "Data Structures",
                "COSC 208",
                25,
                25));

        catalog.addCourse(new Course(
                "MATH 231",
                "Calculus II",
                "MATH 230",
                20,
                12));

        return catalog;
    }
}
