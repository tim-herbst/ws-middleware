import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonumentTableComponent } from './monument-table.component';

describe('MonumentTableComponent', () => {
  let component: MonumentTableComponent;
  let fixture: ComponentFixture<MonumentTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonumentTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MonumentTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
