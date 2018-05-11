package pl.bbkon.microblog.entry;

import pl.bbkon.microblog.user.User;

public interface Votable {

    void upvote(User user);

    void downvote();
}
