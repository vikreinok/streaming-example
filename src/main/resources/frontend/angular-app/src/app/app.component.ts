import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subject, takeUntil} from 'rxjs';

export interface Result {
  status: string;
  percentage: number;
  data: string[];
}

@Component({
  selector: 'streaming-example',
  templateUrl: './app.component.html',
  standalone: true,
})
export class AppComponent implements OnInit, OnDestroy {
  public lastResponse: Result;
  loadedData: string[] = [];

  private destroy$: Subject<void> = new Subject();

  constructor(private cdr: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.consumeSse('api/stream').pipe(
      takeUntil(this.destroy$) // Automatically unsubscribe on destroy
    ).subscribe({
      next: (message) => {
        console.log(message)
        this.lastResponse = message;
        this.loadedData.push.apply(this.loadedData, message.data);
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error with SSE:', err),
    });
  }

  consumeSse(url: string): Observable<Result> {
    return new Observable<Result>((observer) => {
      const eventSource = new EventSource(url);

      eventSource.onmessage = (event) => {

        const data = JSON.parse(event.data);
        observer.next(data);
      };

      eventSource.onerror = (error) => {
        observer.error(error);
        eventSource.close();
      };

      return () => {
        eventSource.close();
      };
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}

