package org.xuxiaoxiao.xiao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.Query;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import org.xuxiaoxiao.xiao.base.BaseFragment;
import org.xuxiaoxiao.xiao.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

//import android.media.SoundPool;

/**
 * Created by WuQiang on 2017/4/10.
 */

public class ChatFragment extends BaseFragment {
    /**
     * private String mUsername;
     * private SyncReference mWilddogRef;
     * private ValueEventListener mConnectedListener;
     * private ChatListAdapter mChatListAdapter;
     */


    private String mUsername;
    private SyncReference mWilddogRef;
    private ValueEventListener mConnectedListener;
    private MessageAdapter mChatListAdapter;
    private EditText inputText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // Make sure we have a mUsername
//        setupUsername();

//        setTitle("Chatting as " + mUsername);

        // Setup our Wilddog mWilddogRef
        mWilddogRef = WilddogSync.getInstance().getReference().child("chat");

    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d("WQ",mChatListAdapter.toString());
//        Log.d("WQ",ListView.toString());
    }

    @Override
    public void onStart() {
        super.onStart();

        // Finally, a little indication of connection status
        mConnectedListener = mWilddogRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(getActivity(), "Connected to Wilddog", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Disconnected from Wilddog", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(SyncError wilddogError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mWilddogRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

//    private void setupUsername() {
//        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
//        mUsername = prefs.getString("username", null);
//        if (mUsername == null) {
//            Random r = new Random();
//            // Assign a random user name if we don't have one saved.
//            mUsername = "WilddogUser" + r.nextInt(100000);
//            prefs.edit().putString("username", mUsername).commit();
//        }
//    }

    private void sendMessage() {
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            // Create a new, auto-generated child of that chat location, and save our chat data there
            String key = mWilddogRef.push().getKey();
            ChatMessage chat = new ChatMessage(input, user.getName(), key);
//            Log.d("WQ_ChatFragment", key);
            mWilddogRef.child(key).setValue(chat);
            inputText.setText("");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.chat_top_fragment, container, false);
        final RecyclerView messageRecyclerView = (RecyclerView) view.findViewById(R.id.message_recycler_view);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final MessageAdapter messageAdapter = new MessageAdapter(mWilddogRef.limitToLast(50));
        messageRecyclerView.setAdapter(messageAdapter);


        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
//        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        // registerAdapterDataObserver

        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                messageRecyclerView.scrollToPosition((messageAdapter.getItemCount() - 1));
            }
        });

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        inputText = (EditText) view.findViewById(R.id.message_input);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        view.findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        inputText = (EditText) view.findViewById(R.id.message_input);

        return view;
    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

        ///////////
        private Query mRef;
        //        private Class<ChatMessage> mModelClass;
        private List<ChatMessage> mModels;
        private List<String> mKeys;
        private ChildEventListener mListener;
//    private OkHttpClient okHttpClient;
        ////////////////////////////////

//        private Activity activity;

//        private User user;
//        private SoundPool soundPool;
//        private XiaoApplication application;

        public MessageAdapter(Query mRef) {

//            this.activity = activity;
//            this.application = application;
            this.mRef = mRef;
//            this.soundPool = application.getSoundPool();
//            this.user = application.getUser();

            mModels = new ArrayList<>();
            mKeys = new ArrayList<String>();
            // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
            mListener = this.mRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                    ChatMessage model = (ChatMessage) dataSnapshot.getValue(ChatMessage.class);
                    String key = dataSnapshot.getKey();

                    // Insert into the correct location, based on previousChildName
                    if (previousChildName == null) {
                        mModels.add(0, model);
                        mKeys.add(0, key);
                    } else {
                        int previousIndex = mKeys.indexOf(previousChildName);
                        int nextIndex = previousIndex + 1;
                        if (nextIndex == mModels.size()) {
                            mModels.add(model);
                            mKeys.add(key);
                        } else {
                            mModels.add(nextIndex, model);
                            mKeys.add(nextIndex, key);
                        }
                    }
                    notifyDataSetChanged();
//                    if (application.isAlarmOn) {
//                        soundPool.playSoundDestroy();
//
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    // One of the mModels changed. Replace it in our list and name mapping
                    String key = dataSnapshot.getKey();
                    ChatMessage newModel = (ChatMessage) dataSnapshot.getValue(ChatMessage.class);
                    int index = mKeys.indexOf(key);

                    mModels.set(index, newModel);

                    notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                    // A model was removed from the list. Remove it from our list and the name mapping
                    String key = dataSnapshot.getKey();
                    int index = mKeys.indexOf(key);

                    mKeys.remove(index);
                    mModels.remove(index);

                    notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                    // A model changed position in the list. Update our list accordingly
                    String key = dataSnapshot.getKey();
                    ChatMessage newModel = (ChatMessage) dataSnapshot.getValue(ChatMessage.class);
                    int index = mKeys.indexOf(key);
                    mModels.remove(index);
                    mKeys.remove(index);
                    if (previousChildName == null) {
                        mModels.add(0, newModel);
                        mKeys.add(0, key);
                    } else {
                        int previousIndex = mKeys.indexOf(previousChildName);
                        int nextIndex = previousIndex + 1;
                        if (nextIndex == mModels.size()) {
                            mModels.add(newModel);
                            mKeys.add(key);
                        } else {
                            mModels.add(nextIndex, newModel);
                            mKeys.add(nextIndex, key);
                        }
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(SyncError syncError) {
                    Log.e("WilddogListAdapter", "Listen was cancelled, no more updates will occur");
                }

            });
        }

        public void cleanup() {
            // We're being destroyed, let go of our mListener and forget about all of the mModels
            mRef.removeEventListener(mListener);
            mModels.clear();
            mKeys.clear();
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.chat_message, parent, false);
            return new MessageViewHolder(view);
//            return null;
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            holder.bind(mModels.get(position));
        }

        @Override
        public int getItemCount() {
            return mModels.size();
        }
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView msg;

        private TextView msgID;

        private LinearLayout layout;

        private ChatMessage mChatMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.msg);

            msgID = (TextView) itemView.findViewById(R.id.msgid);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);

            itemView.setOnClickListener(this);
        }

        public void bind(ChatMessage chatMessage) {
            mChatMessage = chatMessage;
            boolean isMine = mChatMessage.getAuthor() != null && mChatMessage.getAuthor().equals(user.getName());

            msg.setText(mChatMessage.getMessage());
            msgID.setText(mChatMessage.getMessageID());

            layout.setBackgroundResource(isMine ? R.drawable.message_right : R.drawable.message_left);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (!isMine) {
                params.gravity = Gravity.LEFT;
            } else {
                params.gravity = Gravity.RIGHT;
            }
            layout.setLayoutParams(params);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
