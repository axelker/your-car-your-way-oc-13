import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisioContainerComponent } from './visio-container.component';

describe('VisioContainerComponent', () => {
  let component: VisioContainerComponent;
  let fixture: ComponentFixture<VisioContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisioContainerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisioContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
