package com.example.zoomsoft;
// The profile item
public class Profile{
    String item;

    public Profile(String item) {
        this.item = item;
    }
}



//public class Profile extends Fragment {
//    private Button addFriendButton;
//    public static String email = MainPageTabs.email;
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        {
//            User user = new User("alpha123");
//            List<User> list = new ArrayList<>();
//            View view = inflater.inflate(R.layout.profile, container, false);
//
////            addFriendButton = view.findViewById(R.id.addFriend); //can't find the widget by androidStudio @Raji
//            addFriendButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), AddFriends.class);
//                    startActivity(intent);
//                }
//            });
//            return view;
//        }
//    }
//}