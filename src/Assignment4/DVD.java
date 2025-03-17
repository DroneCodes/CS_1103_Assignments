package Assignment4;

public class DVD {
    private int runtime;
    private String director;
    private String rating;

    public DVD(int runtime, String director, String rating) {
        this.runtime = runtime;
        this.director = director;
        this.rating = rating;
    }

    // Getters and setters
    public int getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "DVD [Runtime: " + runtime + " mins, Director: " + director + ", Rating: " + rating + "]";
    }
}
