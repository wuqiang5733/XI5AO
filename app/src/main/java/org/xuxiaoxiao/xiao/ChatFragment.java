package org.xuxiaoxiao.xiao;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.Query;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xuxiaoxiao.xiao.base.BaseFragment;
import org.xuxiaoxiao.xiao.infrastructure.EBHiddFuncPanel;
import org.xuxiaoxiao.xiao.infrastructure.Internet;
import org.xuxiaoxiao.xiao.infrastructure.SendEmotion;
import org.xuxiaoxiao.xiao.infrastructure.ToggleFunctionPanel;
import org.xuxiaoxiao.xiao.model.ChatMessage;

import java.io.ByteArrayOutputStream;
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
    //    private MessageAdapter mChatListAdapter;
    private EditText inputText;

    // 一个实现 Callbacks 的对象，
    // 将来会 在 onAttach 方法当中 把 Activity 转换成 Callbacks
    // 并赋给这个变量
    private MessageAdapter messageAdapter;
    private RecyclerView messageRecyclerView;

    public static final int MEDIA_TYPE_TEXT = 0;
    public static final int MEDIA_TYPE_PHOTO = 1;

    /**
     * Required interface for hosting activities.
     * <p>
     * private Callbacks mCallbacks;
     * <p>
     * public interface Callbacks {
     * // 接口定义了需要 Activity 做的事情
     * // 只要一个 Activity实现了这个接口
     * // Fragment 就有了可以调用 Activity 当中 函数的办法
     * //        void onCrimeSelected(Crime crime);
     * void onFunctionPanelSelected();
     * void hideFunctionPanel();
     * }
     *
     * @Override // 注意传进来的是 Context
     * public void onAttach(Context context) {
     * super.onAttach(context);
     * mCallbacks = (Callbacks) context;
     * }
     * @Override public void onDetach() {
     * super.onDetach();
     * mCallbacks = null;
     * }
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // Make sure we have a mUsername
//        setupUsername();

//        setTitle("Chatting as " + mUsername);

        // Setup our Wilddog mWilddogRef
        mWilddogRef = WilddogSync.getInstance().getReference().child("chat");
//        new DownloadTask().execute();
        new PutBmob().execute();
//        new PutBmob().execute();

//        InputStream in = getActivity().getApplicationContext().getResources().openRawResource(R.drawable.imgdemo);

    }

    @Override
    public void onResume() {
        super.onResume();
//        updateUI();
//        Log.d("WQ",mChatListAdapter.toString());
//        Log.d("WQ",ListView.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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
        EventBus.getDefault().unregister(this);
        mWilddogRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        // 不要 messageAdapter.cleanup(); 程序 就不会Crash 不知道为什么
//        messageAdapter.cleanup();
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
            ChatMessage chat = new ChatMessage(input, user.getName(), key, 0, "T");
//            Log.d("WQ_ChatFragment", key);
            mWilddogRef.child(key).setValue(chat);
            inputText.setText("");
        }
    }
    /*
    public void sendEmotion(String emotionName) {
        String key = mWilddogRef.push().getKey();
        ChatMessage chat = new ChatMessage(emotionName, user.getName(), key,1);
//            Log.d("WQ_ChatFragment", key);
        mWilddogRef.child(key).setValue(chat);
        inputText.setText("");
    }
    */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendEmotion event) {
        String key = mWilddogRef.push().getKey();
        ChatMessage chat = new ChatMessage(event.getEmotionName(), user.getName(), key, 1, "");
//            Log.d("WQ_ChatFragment", key);
        mWilddogRef.child(key).setValue(chat);
        inputText.setText("");
    }

    public void showKeyboard(View v) {
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(inputText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyboard(View v) {
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.chat_top_fragment, container, false);
        messageRecyclerView = (RecyclerView) view.findViewById(R.id.message_recycler_view);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        Button sendEmotion = (Button) view.findViewById(R.id.function_button);

        sendEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击，跳出表情包部分
                hideKeyboard(view);
                inputText.clearFocus();
                // RecyclerView 拉到最底
                messageAdapter.notifyDataSetChanged();
//                mCallbacks.onFunctionPanelSelected();
                EventBus.getDefault().post(new ToggleFunctionPanel());
            }
        });


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
        inputText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            // 输入信息文本获得焦点时
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
//                    mHiddenView.setVisibility(View.GONE);
//                    mCallbacks.hideFunctionPanel();
                    EventBus.getDefault().post(new EBHiddFuncPanel());
                    messageAdapter.notifyDataSetChanged();

                } else {
                    // 此处为失去焦点时的处理内容
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    public void updateUI() {
        // 这个方法是在 onResume 方法当中调用的，
        // 也就是让整个 View 在页面重新呈现的时候
        // 用全新的数据刷新一下，
        // 因为所有的操作其实都是针对  Model(数据) 的，
        // 所以 Model 变化之后，View也应该及时的刷新
//        CrimeLab crimeLab = CrimeLab.get(getActivity());
//        List<Crime> crimes = crimeLab.getCrimes();

        if (messageAdapter == null) {
            messageAdapter = new MessageAdapter(mWilddogRef.limitToLast(20));
            messageRecyclerView.setAdapter(messageAdapter);
        } else {
//            mAdapter.setCrimes(crimes);
            // 让所有的数据都更新，非常没有效率
            messageAdapter.notifyDataSetChanged();
        }
        // 更新 UI 的时候，连同 Subtitle 一起更新
//        updateSubtitle();
    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

        private Query mRef;
        private List<ChatMessage> mModels;
        private List<String> mKeys;
        private ChildEventListener mListener;


        public MessageAdapter(Query mRef) {
            this.mRef = mRef;

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
        public int getItemViewType(int position) {
            return mModels.get(position).getMediaType();
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0: {
                    View view = getActivity().getLayoutInflater().inflate(R.layout.media_type_text, parent, false);
                    return new TextMessageViewHolder(view);
                }

                case 1: {
                    View view = getActivity().getLayoutInflater().inflate(R.layout.media_type_photo, parent, false);
                    return new PhotoMessageViewHolder(view);
                }
                default:
                    break;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            if (holder.getItemViewType() == 0) {
                TextMessageViewHolder textViewHolder = (TextMessageViewHolder) holder;
                textViewHolder.bind(mModels.get(position));
            } else {
                PhotoMessageViewHolder textViewHolder = (PhotoMessageViewHolder) holder;
                textViewHolder.bind(mModels.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mModels.size();
        }
    }

    private abstract class MessageViewHolder extends RecyclerView.ViewHolder {
//        private TextView msgID;
//        private LinearLayout layout;
//        private ChatMessage mChatMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(ChatMessage chatMessage);

    }

    private class TextMessageViewHolder extends MessageViewHolder {
        private TextView msg;

        private TextView msgID;

        private LinearLayout layout;

        private ChatMessage mChatMessage;


        public TextMessageViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.msg);

            msgID = (TextView) itemView.findViewById(R.id.msgid);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);

//            itemView.setOnClickListener(this);
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
    }

    private class PhotoMessageViewHolder extends MessageViewHolder {
        private ImageView imageView;
        private TextView msgID;

        private LinearLayout layout;

        private ChatMessage mChatMessage;

        public PhotoMessageViewHolder(View itemView) {
            super(itemView);

//            msgID = (TextView) itemView.findViewById(R.id.msgid);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void bind(ChatMessage chatMessage) {
            mChatMessage = chatMessage;
            boolean isMine = mChatMessage.getAuthor() != null && mChatMessage.getAuthor().equals(user.getName());

            Picasso.with(getActivity()).load("http://services.hanselandpetal.com/photos/camellia.jpg")
//            Picasso.with(getActivity()).load("http://omnxgkkjc.bkt.clouddn.com/IMG_0763.JPG")
                    .fit().centerCrop()
                    .placeholder(R.drawable.imgdemo)
                    .error(R.drawable.imgdemo).into(imageView);

//            msg.setText(mChatMessage.getMessage());
            // 显示 ID
//            msgID.setText(mChatMessage.getMessageID());

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
    }

    private class DownloadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            new Internet().fetchItems();
            return null;
        }
//
//        @Override
//        protected void onPostExecute(List<GalleryItem> items) {
//            mItems = items;
//            setupAdapter();
//        }
    }

    private class PutBmob extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            new Internet().BmobPostPhoto(getResources().getDrawable(R.drawable.imgdemo), "photo");
            return null;
        }
//
//        @Override
//        protected void onPostExecute(List<GalleryItem> items) {
//            mItems = items;
//            setupAdapter();
//        }
    }

    private class UploadImage extends AsyncTask<Void, Void, Void> {
        Bitmap image;
        String name;

        public UploadImage(Bitmap image, String name) {
            this.image = image;
            this.name = name;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Hold the bite representation of the image
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            // Have a String representation of the image
            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
            return null;
        }
    }
}

/**
 * mChatMessage = chatMessage;
 * boolean isMine = mChatMessage.getAuthor() != null && mChatMessage.getAuthor().equals(user.getName());
 * <p>
 * msgID.setText(String.valueOf(chatMessage.getMessageID()));
 * <p>
 * layout.setBackgroundResource(isMine ? R.drawable.message_right : R.drawable.message_left);
 * LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
 * LinearLayout.LayoutParams.WRAP_CONTENT);
 * <p>
 * if (!isMine) {
 * params.gravity = Gravity.LEFT;
 * } else {
 * params.gravity = Gravity.RIGHT;
 * }
 * layout.setLayoutParams(params);
 */
