import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'streaming-example',
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  progressMessages: { status: string; percentage: number }[] = [];
  isCompleted = false;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.consumeTaskStream();
  }

  consumeTaskStream(): void {
    const eventSource = new EventSource('api/stream', { withCredentials: false },); // Using EventSource for streaming

    eventSource.onmessage = (event) => {
      console.log(event)
      const data = JSON.parse(event.data);
      this.progressMessages.push(data);

      if (data.status === 'Done' && data.percentage === 100) {
        this.isCompleted = true;
        eventSource.close();
      }
    };

    eventSource.onerror = (error) => {
      console.error('Stream encountered an error', error);
      eventSource.close();
    };
  }

constructor() {
} }

