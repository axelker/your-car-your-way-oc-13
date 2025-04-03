import { TestBed } from '@angular/core/testing';

import { VideoconferencingService } from './videoconferencing.service';

describe('VideoconferencingService', () => {
  let service: VideoconferencingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VideoconferencingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
