import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvaibleUserListComponent } from './avaible-user-list.component';

describe('AvaibleUserListComponent', () => {
  let component: AvaibleUserListComponent;
  let fixture: ComponentFixture<AvaibleUserListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvaibleUserListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AvaibleUserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
