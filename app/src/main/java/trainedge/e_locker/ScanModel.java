package trainedge.e_locker;

import com.einmalfel.earl.Enclosure;
import com.google.common.primitives.Bytes;
import com.google.firebase.database.DataSnapshot;

import static android.R.attr.data;


class ScanModel {
    String description;
    String url;
    String userid;
    String imageLink,title;
    char[] publishDate;
    Enclosure item;
    private String key;
    private Long uploaded_on;
    private String  name;

    public ScanModel(DataSnapshot dataSnapshot) {
        this.description = dataSnapshot.child("desc").getValue(String.class);
        this.url = dataSnapshot.child("url").getValue(String.class);
        this.userid = dataSnapshot.child("userid").getValue(String.class);
        key = dataSnapshot.getKey();
        uploaded_on = dataSnapshot.child("uploaded_on").getValue(Long.class);
    }

    public String getDescription() {
        return description;
    }

    public String getUserid() {
        return userid;
    }

    public String getKey() {
        return key;
    }

    public Long getUploaded_on() {
        return uploaded_on;
    }

    public String getUrl() {

        return url;
    }

    ScanModel(DataSnapshot snapshot, String android) {
    }

    public String getName() {
        return name;
    }

    public char[] getPublishDate() {
        return publishDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Enclosure getItem() {
        return item;
    }

    public String getTitle() {
        return title;
    }
}
