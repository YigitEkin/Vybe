package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Friendship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendshipDTO {
    private String senderUsername;
    private String receiverUsername;
    private boolean accepted;

    public FriendshipDTO(Customer sender, Customer receiver, boolean accepted) {
        this.senderUsername = sender.getUsername();
        this.receiverUsername = receiver.getUsername();
        this.accepted = accepted;
    }

    public FriendshipDTO(Customer sender, Customer receiver) {
        this.senderUsername = sender.getUsername();
        this.receiverUsername = receiver.getUsername();
        this.accepted = false;
    }

    public FriendshipDTO(Friendship friendship) {
        this.senderUsername = friendship.getSender().getUsername();
        this.receiverUsername = friendship.getReceiver().getUsername();
        this.accepted = friendship.isAccepted();
    }
}
