package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsup.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import Models.MesssageModel;

public class ChattingAdapter extends RecyclerView.Adapter{

    ArrayList<MesssageModel> list;
    Context context;
    public ChattingAdapter(ArrayList<MesssageModel> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    int SENDER_VIEW_TYPE=1;
    int RECIEVER_VIEW_TYPE=2;
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sendermessaging,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.recievermessaging,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MesssageModel messsageModel=list.get(position);
        if (holder instanceof SenderViewHolder) {
            SenderViewHolder senderHolder = (SenderViewHolder) holder;
            if (senderHolder.sendermessage != null) {
                senderHolder.sendermessage.setText(messsageModel.getMessage());
            }
        } else if (holder instanceof RecieverViewHolder) {
            RecieverViewHolder receiverHolder = (RecieverViewHolder) holder;
            if (receiverHolder.recievermessage != null) {
                receiverHolder.recievermessage.setText(messsageModel.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public int getItemViewType(int position)
    {
        if(list.get(position).getMessageId()!= null && list.get(position).getMessageId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECIEVER_VIEW_TYPE;
        }
    }
    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView recievermessage;
        TextView recievertime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recievermessage=itemView.findViewById(R.id.senderText);
            recievertime=itemView.findViewById(R.id.senderTime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView sendermessage;
        TextView sendertime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermessage=itemView.findViewById(R.id.senderText);
            sendertime=itemView.findViewById(R.id.senderTime);
        }
    }
}
