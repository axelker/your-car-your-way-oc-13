import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeSupportPageComponent } from './home-support-page.component';

describe('HomeSupportPageComponent', () => {
  let component: HomeSupportPageComponent;
  let fixture: ComponentFixture<HomeSupportPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeSupportPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeSupportPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
