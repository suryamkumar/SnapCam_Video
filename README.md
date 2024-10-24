The project consists of two main Activities MainActivity and Service.

MainActivity: This folder includes functionality for capturing video using CameraX. 
The implementation allows for seamless video recording, ensuring high-quality output and efficient resource management.

Service: This folder contains coroutine functions that manage the recording process. 
One of the key features is a timer that increments the filename every 10 minutes, ensuring organized file storage. 
While the coroutine handles the timing and filename increment, other threads continue to manage the video recording process, allowing for smooth operation without interruptions.

