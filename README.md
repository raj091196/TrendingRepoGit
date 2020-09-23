# TrendingRepoGit

Fetch the trending git repository from the git open api and manage it locally using room.

Decisions :-
1. Using Retrofit for getting api data.
2. Using Room ORM to map the SQLite data to java/kotlin Objects.
3. Using Dagger2 for dependency injection to provide loose coupling
4. Using Glide for loading image from the URL

Packages

1. DI package - All Dagger DI related classes
2. Extension - Add Custom extension classes
3. LocalDataBase - For Room Related Activities
4. Repository - All Repositories
5. ViewModels - All ViewModel Classes
6. Views - UI Related Classes. (Activity and Fragment)

Dependencies

1. Retrofit and okHttpClient - for network calls.

2. Glide - For image Loading.

3. Dagger - For Dependency Injection

4. Room - ORM for SQLite
