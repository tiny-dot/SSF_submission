package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class Notice {
   
    @NotNull(message="Please include a title")
    @NotEmpty(message="Title cannot be empty")
    @Size(min=3, max=128, message="title must be within 3 to 128 characters long")
    private String title;

    @NotNull(message="Mandatory field")
    @NotEmpty(message="Cannot be empty")
    @Email(message="must be a well-formed email address")
    private String poster;

    @Future (message="Must be a future date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Mandatory field")
    private Date postDate;

    @NotNull(message="Must include at least 1 category")
    @NotEmpty(message="Must include at least 1 category")
    private String category;

    private List<String> categories;


    @NotNull(message="Mandatory field")
    @NotEmpty(message="Mandatory field")
    private String text;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    @Override
    public String toString() {
        return "Notice [title=" + title + ", poster=" + poster + ", postDate=" + postDate + ", categories=" + categories
                + ", text=" + text + "]";
    }  
    
}
