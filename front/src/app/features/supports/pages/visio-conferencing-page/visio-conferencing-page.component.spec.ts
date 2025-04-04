import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisioConferencingPageComponent } from './visio-conferencing-page.component';

describe('VisioConferencingPageComponent', () => {
  let component: VisioConferencingPageComponent;
  let fixture: ComponentFixture<VisioConferencingPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisioConferencingPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisioConferencingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
