import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MonumentService {

  private apiUrl = 'http://localhost:9001/monuments';

  constructor(private http: HttpClient) {
  }

  getMonuments(page: number, size: number, sort: string, filters: string) {
    const appendix = filters == '' ? '?' : '&';
    return this.http.get(this.apiUrl + filters + appendix + 'page=' + page + '&size=' + size + '&sort=' + sort);
  }
}
